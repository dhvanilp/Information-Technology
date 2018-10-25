from random import randrange,choice,uniform,randint,seed
from nb import *
def SPCrossover(p1,p2):
	index=randint(0,len(p1)-1)
	c1=p1[:index]+p2[index:]
	c2=p2[:index]+p1[index:]
	return [c1,c2]

def TPCrossover(p1,p2):
	index1=randint(1,len(p1)-2)
	index2=randint(1,len(p2)-2)
	if index1>index2:
		index1,index2=index2,index1
	c1=p1[:index1]+p2[index1:index2]+p1[index2:]
	c2=p2[:index1]+p1[index1:index2]+p2[index2:]
	return [c1,c2]

def getRandomBinaryChromosome(n):
	ans=[]
	for i in range(n):
		ans.append(choice(["1","0"]))
	return ans

seed(0)
done=False
limit=100
conv_limit=0.1
counter=0
oldfitness=[]
pop_size=10
crossover_rate=0.3
mutation_rate=0.25
n=nb(0)
# Initialize population randomly
population=[getRandomBinaryChromosome(n) for i in range(pop_size)]
while not done and counter<limit:
	counter+=1
	#print population
	done=True
	# Fitness evaluation
	fitness=[nb(None,i) for i in population]
	if oldfitness!=[]:
		for i in range(pop_size):
			if abs(fitness[i]-oldfitness[i])>conv_limit:
				done=False
		if done:
			print "Fitness converged! Iteration %d" % counter
			break
	else:
		done=False
	#print fitness

	# Genetic operators:
	# Selection:
	p_fitness=[i/sum(fitness) for i in fitness]
	cumulative_fitness=[0 for i in range(pop_size)]
	cumulative_fitness[0]=p_fitness[0]
	for i in range(1,pop_size):
		cumulative_fitness[i]=p_fitness[i]+cumulative_fitness[i-1]
	#print cumulative_fitness
	selected=[]
	for i in range(pop_size):
		r=randrange(0,1)
		if r<cumulative_fitness[0]:
			selected.append(0)
		elif r>cumulative_fitness[-1]:
			selected.append(pop_size-1)
		else:
			j=0
			while r>cumulative_fitness[j]:
				j+=1
			selected.append(j)
	newpopulation=[population[i] for i in selected]
	population=newpopulation

	# Crossover:
	num_selected=int(pop_size*crossover_rate)
	selected=[randint(0,pop_size-1) for i in range(num_selected)]
	#print selected
	c_selected=[population[i] for i in selected]
	for i in range(num_selected):
		j=(i+1)%num_selected
		[population[selected[i]],population[selected[j]]]=TPCrossover(c_selected[i],c_selected[j])

	# Mutation:
	genes=n*pop_size
	selected_genes=int(genes*mutation_rate)
	selected=[randint(0,genes-1) for i in range(selected_genes)]
	for i in selected:
		#print i,n
		if population[int(i/n)][int(i%n)]=="0":
			population[int(i/n)][int(i%n)]="1"
		else:
			population[int(i/n)][int(i%n)]="0"
	oldfitness=fitness[:]

maxv=fitness[0]
#print type(maxv)
maxi=0
for i in range(1,pop_size):
	if fitness[i]>maxv:
		maxv=fitness[i]
		maxi=i
#print fitness,counter
print "Max accuracy obtained: %f for chromosome %s..." % (maxv,''.join(population[maxi]))