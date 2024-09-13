import re, sys, turtle

SCREEN_SIZE = 500


def setScales(scaleArray):

    arrLength = len(scaleArray)
    newArr = [1]

    for i in range(1, arrLength):
        temp = 1 / (scaleArray[0] / scaleArray[i])
        newArr.append(temp)
    
    return(newArr)

def errorMessage():
    print("Input not valid")


def drawRecursion(index, array, x, y, t):
    if (index == len(array)):
        return
    
    size = array[index][0]

    listDraw.append([index, x, y, size, int(array[index][1]), int(array[index][2]), int(array[index][3]), t])

    leftTopX = x - size
    leftTopY = y + size

    rightTopX = x + size
    rightTopY = y + size

    leftBottomX = x - size
    leftBottomY = y - size

    rightBottomX = x + size
    rightBottomY = y - size

    drawRecursion(index + 1, array, leftTopX, leftTopY, t)
    drawRecursion(index + 1, array, rightTopX, rightTopY, t)
    drawRecursion(index + 1, array, leftBottomX, leftBottomY, t)
    drawRecursion(index + 1, array, rightBottomX, rightBottomY, t)


def openFile():
    try: 
        lines = []
        scaleArr = []
        scaleArrPix = []

        s = turtle.getscreen()
        s.setup(SCREEN_SIZE, SCREEN_SIZE)
        t = turtle.Turtle()
        turtle.tracer(0, 0)
        turtle.colormode(255)
        

        for line in sys.stdin:
            singleLine = line.rstrip().split()
            lines.append(singleLine)
        
        for i in lines:
            scaleArr.append(float(i[0]))

        scaleArr = setScales(scaleArr)
        
        size = 0.1
        for i in scaleArr:
            size += i

        for i in range(len(scaleArr)):
            lines[i][0] = (250 * scaleArr[i] / size)

        drawRecursion(0, lines, 0, 0, t)
        l = sorted(listDraw, key = lambda x: x[0])

        for i in l:
            drawSquare(i[1], i[2], i[3], i[4], i[5], i[6], i[7])
        turtle.done()
    except:
       errorMessage()
       return


def drawSquare(x, y, size, r, g, b, t):
    t.penup()
    t.fillcolor((r, g, b))
    t.pencolor((r, g, b))

    t.goto(x - size, y + size)
    t.pendown()
    t.begin_fill()

    t.goto(x + size, y + size)
    t.goto(x + size, y - size)
    t.goto(x - size, y - size)
    t.goto(x - size, y + size)

    t.end_fill()
    t.penup()

listDraw = []
openFile()