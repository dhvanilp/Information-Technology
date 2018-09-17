#include<stdio.h>
#include<stdlib.h>
// #include<omp.h>
int ARR_SIZE = 100;
int main(){
    
    int a[ARR_SIZE];
    for(int i=0;i<ARR_SIZE;i++){
        a[i]=i;
    }
    int key=0;
    printf("Enter the key to search:\n");
    scanf("%d",&key);
    
    for(int i=0;i<ARR_SIZE;i++){
        printf("%d ",a[i]);
    }
    // printf("hello\n");
    int low=0;
    int high=ARR_SIZE-1;
    int mid=(low+high)/2;
    int index=-1;
    while(low<=high){
        if(a[mid]==key){
            index=mid;
        }
        else if(a[mid]>key){
            high=mid-1;
        }else{
            low=mid+1;
        }
        mid=(low+high)/2;
    }
    printf("The key is found at %d\n",index);

    return 0;
}