#include<bits/stdc++.h>
using namespace std;
int main(){
    int n,m;
    cin>>n>>m;
    string a,b;
    cin>>a>>b;
    string xaaa;
    int i=0,j=0;
    int out=0;
    int out2=0;


    while(1){
        if(i==n&&j==m){
            printf("YES");
            out=1;
            break;
        }
        else if(a[i]==b[j]){
            a[i]=b[j]='_';
            i++;
            j++;
        }
        else if(a[i]=='*'){
            break;
        }
        else if(a[i]!=b[i]){
            cout<<"NO";
            out=1;
            out2=1;
            break;
        }
    }
    int ab=0;
    if(!out){
        int x,y;
        for(x=a.length()-1,y=b.length()-1;x>i;x--,y--){
            if(a[x]!=b[y]){
                cout<<"NO";
                out2=1;
                break;
            }

        }
    }
    if(!out2){
        cout<<"YES";
    }
    
}