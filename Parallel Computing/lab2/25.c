#include<stdio.h>
#include<omp.h>
void main()
{
    int i,n,sum=0;
    scanf("%d",&n);
    int a[n];
    for(int i=0;i<n;i++){scanf("%d",&a[i]);}

    #pragma omp parallel for reduction(+:sum)
    for(i=0;i<n;i++)
    {
        sum+=a[i];
        printf("%d\n",i);
    }
    printf("%d",sum);
}
