import csv

def predict(row, weights):
    activation = weights[0]
    for i in range(len(row)-1):
    	activation += weights[i + 1] * float(row[i])
    return 1.0 if activation >= 0.0 else 0.0

def train_weights(train, l_rate, n_epoch):
	weights = [0.0 for i in range(len(train[0]))]
	for epoch in range(n_epoch):
		for row in train:
			prediction = predict(row, weights)
			error = row[-1] - prediction
			weights[0] = weights[0] + l_rate * error
			for i in range(len(row)-1):
				weights[i + 1] = weights[i + 1] + l_rate * error * float(row[i])
		print('>epoch=%d, lrate=%.3f' % (epoch, l_rate))
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
        
    weights=train_weights(dataset,0.2 ,10)
    print(weights)
    file.close()
    # prediction from here
    file = open("IRIS.csv", "r")
    reader=csv.reader(file)
    lineNo=0
    count=0
    for line in reader:
        if lineNo == 0:
            lineNo = 1
            continue
        lineNo+=1
        expected=1    
        if line[len(line)-1]=="Iris-setosa":
            expected=0
        temp=line[:len(line)-1]
        temp.append(expected)
        prd=predict(temp,weights)
        if prd==expected:
            count+=1
    
    accuracy=count/(lineNo-1)
    print(accuracy*100)


    
if __name__ == '__main__':
    main()