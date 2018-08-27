#include<stdio.h>
#include<omp.h>

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
    int sum=0;
    #pragma omp parallel shared(a,n)
    {   
        #pragma omp parallel for collapse(2)          
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(j==0)
                        sum=0;
                    sum+=a[i][j];
                    printf("thread %d\n", omp_get_thread_num());
                    if(j==n-1)
                    printf("sum row %d is %d, thread = %d\n",i,sum,omp_get_thread_num());
                }
                
            }
    
        // for(int i=0;i<n;i++){
        //     sum=0;
        //     #pragma omp for reduction(+:sum)
        //     for(int j=0;j<n;j++){
        //         sum+=a[j][i];
        //     }
        //     printf("sum col %d is %d, thread = %d\n",i,sum,omp_get_thread_num());
        // } 
        
    }
    return 0;
}