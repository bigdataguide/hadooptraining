package cn.chinahadoop.mapreduce;

import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.map.RegexMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.Text;

public class Grep extends Configured implements Tool {
	private Grep() {
	} // singleton

	public int run(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Grep <inDir> <outDir> <regex> [<group>]");
			ToolRunner.printGenericCommandUsage(System.out);
			return 2;
		}
        // the temp dir between two mapreduce jobs
		Path tempDir = new Path("grep-temp-" + Integer.toString(new Random().nextInt(Integer.MAX_VALUE)));

		Configuration conf = getConf();
		conf.set(RegexMapper.PATTERN, args[2]);
		if (args.length == 4)
			conf.set(RegexMapper.GROUP, args[3]);
         //the first job
		// word count
		Job grepJob = new Job(conf);

		try {
            //define the first job
			grepJob.setJobName("grep-search");

			FileInputFormat.setInputPaths(grepJob, args[0]);

			grepJob.setMapperClass(RegexMapper.class);

			grepJob.setCombinerClass(LongSumReducer.class);
			grepJob.setReducerClass(LongSumReducer.class);
            // output to tempDir
			FileOutputFormat.setOutputPath(grepJob, tempDir);
			grepJob.setOutputFormatClass(SequenceFileOutputFormat.class);
			grepJob.setOutputKeyClass(Text.class);
			grepJob.setOutputValueClass(LongWritable.class);
            // result: word + count
			grepJob.waitForCompletion(true);
            //the second job
			//sort
			Job sortJob = new Job(conf);
			sortJob.setJobName("grep-sort");
            //tempDir to input
			FileInputFormat.setInputPaths(sortJob, tempDir);
			sortJob.setInputFormatClass(SequenceFileInputFormat.class);

			sortJob.setMapperClass(InverseMapper.class);
            //just write the sort data out
			sortJob.setNumReduceTasks(1); // write a single file
			FileOutputFormat.setOutputPath(sortJob, new Path(args[1]));
			sortJob.setSortComparatorClass( // sort by decreasing freq
					LongWritable.DecreasingComparator.class);

			FileSystem.get(conf).delete(new Path(args[1]),true);
			
			sortJob.waitForCompletion(true);
		} finally {
			FileSystem.get(conf).delete(tempDir, true);
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Grep(), args);
		System.exit(res);
	}

}
