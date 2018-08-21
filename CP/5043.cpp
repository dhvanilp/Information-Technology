#include<bits/stdc++.h>
using namespace std;
int main(){
    int n,k;
    cin>>n>>k;
    string a;
    cin>>a;
    int open=0;
    int close=0;
    string x="";
    for(int i=0;i<n;i++){
        if(a[i]=='('){
            if(open<k/2){
                x=x+'(';
                open++;
            }
        } else if(a[i]==')'){
           if(close<k/2){
                x=x+')';
                close++;
            }
        }
    }
    cout<<x;

}