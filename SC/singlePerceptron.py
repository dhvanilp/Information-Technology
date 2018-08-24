import csv

def predict(row, weights):
    activation = weights[0]
    for i in range(len(row)-1):
    	activation += weights[i + 1] * row[i]
    return 1.0 if activation >= 0.0 else 0.0

def train_weights(train, l_rate, n_epoch):
	weights = [0.0 for i in range(len(train[0]))]
	for epoch in range(n_epoch):
		sum_error = 0.0
		for row in train:
			prediction = predict(row, weights)
			error = row[-1] - prediction
			sum_error += error**2
			weights[0] = weights[0] + l_rate * error
			for i in range(len(row)-1):
				weights[i + 1] = weights[i + 1] + l_rate * error * row[i]
		print('>epoch=%d, lrate=%.3f, error=%.3f' % (epoch, l_rate, sum_error))
	return weights

def main():
    file = open("IRIS.csv", "r")
    reader=csv.reader(file)
    dataset=[]
    lineNo=0
    for line in reader:
        if lineNo == 0:
            lineNo = 1
            continue
        expected=1    
        if line[len(line)-1]=="Iris-setosa":
            expected=0
        temp=line[:len(line)-1]
        temp.append(expected)
        dataset.append(temp)
        
    weights=train_weights(dataset,0.1,2)
    print(weights)
    
if __name__ == '__main__':
    main()