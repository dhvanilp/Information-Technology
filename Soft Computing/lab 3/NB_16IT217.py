from random import seed
from random import randrange
from random import random
from csv import reader
from math import exp

# Load a CSV file
def load_csv(filename):
	dataset = list()
	with open(filename, 'r') as file:
		csv_reader = reader(file)
		for row in csv_reader:
			if not row:
				continue
			dataset.append(row)
	return dataset

# Convert string column to float
def str_column_to_float(dataset, column):
	for row in dataset:
		row[column] = float(row[column].strip())

# Convert string column to integer
def str_column_to_int(dataset, column):
	class_values = [row[column] for row in dataset]
	unique = set(class_values)
	lookup = dict()
	for i, value in enumerate(unique):
		lookup[value] = i
	for row in dataset:
		row[column] = lookup[row[column]]
	return lookup

# Split a dataset into k folds
def cross_validation_split(dataset, n_folds):
	dataset_split = list()
	dataset_copy = list(dataset)
	fold_size = int(len(dataset) / n_folds)
	for i in range(n_folds):
		fold = list()
		while len(fold) < fold_size:
			index = randrange(len(dataset_copy))
			fold.append(dataset_copy.pop(index))
		dataset_split.append(fold)
	return dataset_split

def classProbability(dataset,attr_num):
	classes=[0.0,1.0]
	probabilities={}
	for i in classes:
		class_tuples=[]
		for j in dataset:
			if j[attr_num]==i:
				class_tuples.append(j)
		probabilities[i]=[len(class_tuples)/float(len(dataset)),class_tuples,i]
	return probabilities

def accuracy_metric(actual, predicted):
	correct = 0
	tp=0
	fp=0
	tn=0
	fn=0
	for i in range(len(actual)):
		if actual[i] == predicted[i]:
			correct += 1
			if actual[i]==0:
				tn+=1
			else:
				tp+=1
		else:
			if predicted[i]==1:
				fp+=1
			else:
				fn+=1
	a=tp/float(tp+fp)
	b=tn/float(tn+fn)
	c=tp/float(tp+fn)
	d=tn/float(tn+fp)
	return [correct / float(len(actual)) * 100.0,a,b,c,d]

def main():
	filename = 'SPECT.csv'
	dataset = load_csv(filename)
	dataset=dataset[1:]
	for i in range(len(dataset[0])):
		str_column_to_float(dataset, i)
	n=len(dataset)
	data_split=cross_validation_split(dataset,10)
	scores=[]
	for fold in data_split:
		trainset1=list(data_split)
		trainset1.remove(fold)
		testset=fold
		trainset=[]
		for i in trainset1:
			for j in i:
				trainset.append(j)
		classes=classProbability(trainset,-1)
		actual,predicted=[],[]
		for row in testset:
			X=row[:-1]
			p_res=[i[0] for i in classes.values()]
			m,m_ind=-1,-1
			for c in range(len(classes)):
				for i in range(len(X)):
					p=classProbability(classes[c][1],i)
					p_res[c]=p_res[c]*p[X[i]][0]
				if m<p_res[c]:
					m=p_res[c]
					m_ind=c
			predicted.append(classes[m_ind][2])
			actual.append(row[-1])
		scores.append(accuracy_metric(actual,predicted))
	acc=[i[0] for i in scores]
	p_prec=[i[1] for i in scores]
	n_prec=[i[2] for i in scores]
	p_rec=[i[3] for i in scores]
	n_rec=[i[4] for i in scores]
	print('Mean Accuracy: %.3f%%' % (sum(acc)/float(len(acc))))
	print('Mean precision(+,-):'+str(sum(p_prec)/float(len(p_prec)))+" and "+str(sum(n_prec)/float(len(n_prec))))
	print('Mean Recall(+,-):'+str(sum(p_rec)/float(len(p_rec)))+" and "+str(sum(n_rec)/float(len(n_rec))))

main()