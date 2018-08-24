import csv
import matplotlib.pyplot as plt

file = open("IRIS.csv", "r")
reader = csv.reader(file)
learningRate = float(input("Enter learning rate:"))
lineNo = 0

SLC = []
SWC = []
PLC = []
PWC = []
OUT = []

for line in reader:
    if lineNo == 0:
        lineNo = 1
        continue

    sl, sw, pl, pw, out = line[0], line[1], line[2], line[3], line[4]
    sl, sw, pl, pw = float(sl), float(sw), float(pl), float(pw)
    SLC.append(sl), SWC.append(sw), PLC.append(pw), PWC.append(pw), OUT.append(out)

sumc1 = 0
sumc2 = 0
sumc3 = 0
sumc4 = 0
for i in range(len(SLC)):
    sumc1 = sumc1 + SLC[i]
    sumc2 = sumc2 + SWC[i]
    sumc3 = sumc3 + PLC[i]
    sumc4 = sumc4 + PWC[i]

mean1 = sumc1 / len(SLC)
mean2 = sumc2 / len(SLC)
mean3 = sumc3 / len(SLC)
mean4 = sumc4 / len(SLC)

for i in range(len(SLC)):
    SLC[i] = SLC[i] - mean1
    SWC[i] = SWC[i] - mean2
    PLC[i] = PLC[i] - mean3
    PWC[i] = PWC[i] - mean4

weightSL = 0.25
weightSW = 0.25
weightPL = 0.25
weightPW = 0.25

for i in range(len(SLC)):
    y = (weightSL * SLC[i]) + (weightSW * SWC[i]) + (weightPL * PLC[i]) + (weightPW * PWC[i])
    z = 1
    if OUT[i] == "Iris-setosa":
        z = 0

    print("This is y, out and z: ", y, OUT[i], z)

    error = z - y

    weightSL = weightSL + learningRate * error * SLC[i]
    weightSW = weightSW + learningRate * error * SWC[i]
    weightPL = weightPL + learningRate * error * PLC[i]
    weightPW = weightPW + learningRate * error * PWC[i]
file.close()

# while True:
#     temp = (input("Enter the values:").split())
#     a, b, c, d = float(temp[0]), float(temp[1]), float(temp[2]), float(temp[3])
#     x = weightSL * (a-mean1) + weightSW * (b-mean2) + weightPL * (c-mean3) + weightPW * (d-mean4)
#     if x>=0.5:
#         x=1
#     else:
#         x=0
#     print(x)
#     if x==0: 
#         print("Iris-setosa")
#     else:
#         print("Iris-versicolor")  
filenew = open("IRIS.csv", "r")

readerX = csv.reader(filenew)
lineNo=0
count=0
for line in readerX:
    # print(line,end = '|')
    if lineNo == 0:
        lineNo = 1
        continue
    lineNo = lineNo + 1
    a, b, c, d, out1 = line[0], line[1], line[2], line[3], line[4]
    a, b, c, d = float(a), float(b), float(c), float(d)
    x = weightSL * (a-mean1) + weightSW * (b-mean2) + weightPL * (c-mean3) + weightPW * (d-mean4)
    # print(x,end='|')
    out2=""
    if x>=1:
        out2="Iris-versicolor"
    else:
        out2="Iris-setosa"
    if out2 == out1:
        count=count+1
    print(out1,":",out2)

lineNo=lineNo-1
print("Accuracy:",count/lineNo*100)
