// By dongxicheng,
// blog:http://dongxicheng.org/
// mapper.cpp
#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include <cstdlib>
using namespace std;
string charArrayToString(char *str) {
  stringstream ss(str);
  return ss.str(); 
}

vector<std::string>& split(
  const string &s, char delim, vector<string> &elems) {
  stringstream ss(s);
  string item;
  while(getline(ss, item, delim)) {
    elems.push_back(item);
  }
 return elems;
}

int main(int argc, char *argv[], char *env[]) {
  int reduce_task_no = -1;
  int iterator = -1;
  vector<string> pairs; 
  for(int i = 0; env[i] != NULL; i++) {
    pairs.clear();
    split(charArrayToString(env[i]), '=', pairs);
    if(pairs.size() < 2) continue;
    if(pairs[0] == "mapreduce_job_reduces") // number of reduce tasks
      reduce_task_no = atoi(pairs[1].c_str());
    else if(pairs[0] == "mapreduce_iterator_no") // user-defined attribute
      iterator = atoi(pairs[1].c_str());
  }
  cerr << "mapreduce.job.reduces:" << reduce_task_no 
       << ",mapreduce.iterator.no:" << iterator << endl;

  string key;
  while(cin >> key) {
    cout << key << "\t" << "1" << endl;
    // Define counter named counter_no in group counter_group
    cerr << "reporter:counter:counter_group,counter_no,1\n";
    // dispaly status 
    cerr << "reporter:status:processing......\n"; 
    // Print logs for testing
    cerr << "This is log, will be printed in stdout file\n";
  }
  return 0;
}
