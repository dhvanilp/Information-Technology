#include<bits/stdc++.h>
using namespace std;
int main(){
    int t;
    cin>>t;
    while(t--){
        vector<int>a;
        int n,p;
        cin>>n>>p;
        for(int i=0;i<n;i++){
            int temp;
            cin>>temp;
            a.push_back(temp);
        }
        sort(a.begin(),a.end());
        for(int i=0;i<n;i++){
            int suml=a[i];
            int sumr=a[i];
        }
    }
    return 0;
}