package cn.chinahadoop.mapreduce;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import sun.misc.Unsafe;

public class OOMTest extends Configured implements Tool {

	public static final int on_heap_length = 1 * 100 * 1000 * 1000;
	public static final int off_heap_length = 1000 * 1000 * 1000;

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		// allocate on heap space
		private byte[] byteArray = new byte[on_heap_length];

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			Field f = null;
			try {
				f = Unsafe.class.getDeclaredField("theUnsafe");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			f.setAccessible(true);
			Unsafe us = null;
			try {
				us = (Unsafe) f.get(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			// allocate off heap space
			long id = us.allocateMemory(off_heap_length);

			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: oom-test <in> [<in>...] <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "oom test");
		job.setJarByClass(OOMTest.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath);

		for (int i = 0; i < otherArgs.length - 1; ++i) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new OOMTest(), args);
		System.exit(res);
	}
}
