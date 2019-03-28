#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int main(){
    int n;
    int tid;
    printf("Enter the no of employees\n");
    scanf("%d",&n);
    int arr[n][2];
    for(int i=0;i<n;i++){
        printf("Enter salary of employee %d\n",i+1);
        arr[i][0]=i+1;
        scanf("%d",&arr[i][1]);
    }
    float sum=0;
    #pragma omp parallel for reduction(+:sum)
    for(int i=0;i<n;i++){
        tid=omp_get_thread_num();
        float x=arr[i][1]*(0.06);
        sum+=x;
        printf("Thread %d sum is  = %f\n",tid,sum);
        if(x>5000){
        float y=x*(0.02);
        sum-=y;
        }
    }
    printf("Sum is %f\n",sum);
    return 0;
}
