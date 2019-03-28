#include<stdio.h>
#include<omp.h>
#include<stdlib.h>
#include<string.h>

int main(){
    int n;
    printf("Enter n:");
    scanf("%d",&n);
    int a[n][n];
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            scanf("%d",&a[i][j]);
        }
    }
    int col[n];
    int row[n];
    memset(col,0,sizeof(col));
    memset(row,0,sizeof(row));
    int sum=0;
    for(int i=0;i<n;i++){
        printf("%d",row[i]);
    }

    #pragma omp parallel for collapse(2)          
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                row[i]+=a[i][j];
                printf("thread %d\n", omp_get_thread_num());
            }
            
        }

    for(int i=0;i<n;i++){
        printf("%d ",row[i]);
    }

    #pragma omp parallel for collapse(2)          
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                col[i]+=a[j][i];
                printf("thread %d\n", omp_get_thread_num());
            }
            
        }
    
    for(int i=0;i<n;i++){
        printf("%d ",col[i]);
    }
        
    
    return 0;
}