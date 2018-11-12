from random import randrange
from csv import reader
import math
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
def str_column_to_float(dataset, column,type=1):
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

# Calculate accuracy percentage
def accuracy_metric(actual, predicted):
	correct = 0
	for i in range(len(actual)):
		if actual[i] == predicted[i]:
			correct += 1
	return correct / float(len(actual)) * 100.0

def main():
	filename = 'SPECT.csv'
	knn=13
	dataset = load_csv(filename)
	dataset=dataset[1:]
	for i in range(len(dataset[0])-1):
		str_column_to_float(dataset, i)
	# convert string class to integers
	str_column_to_int(dataset, len(dataset[0])-1)
	folds=cross_validation_split(dataset,10)
	scores=[]
	for fold in folds:
		predicted=[]
		actual=[]
		trainset1=folds[:]
		trainset1.remove(fold)
		trainset=[]
		for i in trainset1:
			for j in i:
				trainset.append(j)
		testset=fold
		for i in testset:
			d=0.0
			distances=[]
			for j in trainset:
				for k in range(len(j)-1):
					d+=(j[k]-i[k])**2
				distances.append((math.sqrt(d),j[-1]))
			distances.sort(key=lambda x:x[0])
			distances=distances[:knn] 
			print(distances)
			count=[0 for _ in range(2)]
			for j in distances:
				count[j[1]]+=1
			m,ans=-1,-1
			for k in range(len(count)):
				v=count[k]
				if v>m:
					m=v
					ans=k
			predicted.append(ans)
			actual.append(i[-1])
		scores.append(accuracy_metric(actual,predicted))
	print('Scores: %s' % scores)
	print('Mean Accuracy: %.3f%%' % (sum(scores)/float(len(scores))))
main()