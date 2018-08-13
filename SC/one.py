import csv

file=open("IRIS.csv", "r")
reader = csv.reader(file)
weightSL=1/5
weightSW=1/5
weightPL=1/5
weightPW=1/5

learningRate=(input("Enter learning rate:"))

for line in reader:
    sl,sw,pl,pw,out=line[0],line[1],line[2],line[3],line[4]
    z=1
    if out is"Iris-setosa":
        z=0
    # print(sl," ",sw," ",pl," ",pw," ",out)
    y=weightSL*sl+weightSW*sw+weightPL*pl+weightPW*pw
    error=z-y

    weightSL=weightSL+learningRate*(error)*sl
    weightSW=weightSW+learningRate*(error)*sw
    weightPL=weightPL+learningRate*(error)*pl
    weightPW=weightPW+learningRate*(error)*pw

while(True):
    temp=(input("Enter the values:").split())
    a,b,c,d=int(temp[0]),int(temp[1]),int(temp[2]),int(temp[3])
    print(weightSL*a+weightSW*b+weightPL*c+weightPW*d)
