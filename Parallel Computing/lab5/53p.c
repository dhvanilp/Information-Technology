#include<stdio.h>
#include <sys/time.h>
#include <stdlib.h>
#include<omp.h>
#define CHUNKSIZE 5

int ARR_SIZE=1000000;
int main(){
    struct timeval TimeValue_Start;
    struct timezone TimeZone_Start;
    struct timeval TimeValue_Final;
    struct timezone TimeZone_Final;
    long time_start, time_end;
    double time_overhead;
    int chunk=CHUNKSIZE;

    int a[ARR_SIZE];
    int count =0;
    for(int i=0;i<ARR_SIZE;i++){
        a[i]=i*(count);
        count++;
        count=-count;
    }
    gettimeofday(&TimeValue_Start, &TimeZone_Start);
    //program here
    int min=a[0];
    int i;
    #pragma omp parallel for reduction(min:min) schedule(guided,chunk)
    for (i = 0; i < ARR_SIZE; i++)
       min = min > a[i] ? a[i] : min;
    //to here
    gettimeofday(&TimeValue_Final, &TimeZone_Final);
    time_start = TimeValue_Start.tv_sec * 1000000 + TimeValue_Start.tv_usec;
    time_end = TimeValue_Final.tv_sec * 1000000 + TimeValue_Final.tv_usec;
    time_overhead = (time_end - time_start)/1000000.0;
    printf("\nMin is :%d\n",min);
    printf("\n\n\tTime in Seconds (T) : %lf\n",time_overhead);
    return 0;
}