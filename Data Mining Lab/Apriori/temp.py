import math
from itertools import chain,combinations
import time
def read_csv(filename):
	dataset=[]
	f=open(filename, 'r')
	min_itemId=float("inf")
	max_itemId=-1
	count=0
	for line in f:
		count=count+1
        if count>10000:
            break
        transaction=[int(i) for i in line.strip().split(',')]
		max_itemId=max(max_itemId,max(transaction))
		min_itemId=min(min_itemId,min(transaction))
		dataset.append(set(transaction))
	return [dataset,max_itemId,min_itemId]

def checkIfFrequent(itemset,minsupp,dataset):
	supp=0
	for transaction in dataset:
		if len(transaction.intersection(itemset))==len(itemset):
			supp+=1
	if supp>=minsupp:
		return [True,float(supp)]
	return [False,float(supp)]

def powerset(iterable):
	"powerset([1,2,3]) --> () (1,) (2,) (3,) (1,2) (1,3) (2,3) (1,2,3)"
	s = list(iterable)
	return chain.from_iterable(combinations(s, r) for r in range(len(s)+1))

def main():
	start=time.time()
	minconf=0.5
	dataset,max_itemId,min_itemId=read_csv('retail_dataset.csv')
	minsupp=2*len(dataset)/9
	C=[]
	L=[[] for i in range(min_itemId,max_itemId+1)]
	for i in range(min_itemId,max_itemId+1):
		C.append(set([i]))
	i=1
	while True:
		for itemset in C:
			if(checkIfFrequent(itemset,minsupp,dataset)[0] and itemset not in L[i-1]):
				L[i-1].append(itemset)
		if L[i-1]==[]:
			break
		C=[]
		i+=1
		for item1 in range(len(L[i-2])):
			for item2 in range(item1,len(L[i-2])):
				u=L[i-2][item1].union(L[i-2][item2])
				if len(u)==i:
					C.append(u)

	for i in range(len(L)):
		if L[i]!=[]:
			print 'Frequent '+str(i+1)+' itemset:',L[i]

	strong_rules=[]

	for i in range(len(L)-1,-1,-1):
		if L[i]!=[]:
			for fitem in L[i]:
				lhs=list(set(_) for _ in powerset(fitem))
				lhs=lhs[1:]
				rhs=lhs[:]
				for l in lhs:
					for r in rhs:
						if len(l.intersection(r))==0:
							conf=checkIfFrequent(l.union(r),minsupp,dataset)[1]/checkIfFrequent(l,minsupp,dataset)[1]
							if conf>=minconf:
								l1=[str(_) for _ in list(l)]
								r1=[str(_) for _ in list(r)]
								rule=','.join(l1)+'-->'+','.join(r1)
								if rule not in strong_rules:
									strong_rules.append(rule)

	print 'Strong association rules are:'
	print '\n'.join(strong_rules)
	end=time.time()
	print end-start
if __name__ == '__main__':
	main()
