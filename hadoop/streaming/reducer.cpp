// By dongxicheng,
// blog:http://dongxicheng.org/
// reducer.cpp
#include <iostream>
#include <string>

using namespace std;
int main() {
  string cur_key, last_key, value;
  cin >> cur_key >> value;
  last_key = cur_key;
  int n = 1;
  while(cin >> cur_key) {
    cin >> value;
    if(last_key != cur_key) {
      cout << last_key << "\t" << n << endl;
      last_key = cur_key;
      n = 1;
    } else {
      n++;
    }
  }
  cout << last_key << "\t" << n << endl;
  return 0;
}
