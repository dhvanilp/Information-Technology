#include<bits/stdc++.h>
using namespace std;
int main(){
    long long int n,k;
    cin>>n>>k;
    long long int upper=max(n,k-1);
    long long int ans=0;
    if(n>=k){
        ans=(k-1)/2;
    }else{
        if(2*n>k){
            ans=n-k/2;
        }
    }
    cout<<ans;

}