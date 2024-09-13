import re
import turtle
import math
import time

SCREEN_SIZE = 500
CUBE_SIZE = 30

y = "yellow"
b = "blue"

CUBE_ONE_COLOR = y
CUBE_TWO_COLOR = b
CUBE_THREE_COLOR = y
CUBE_FOUR_COLOR = b
CUBE_FIVE_COLOR = b
CUBE_SIX_COLOR = y
CUBE_SEVEN_COLOR = b

def drawCubes():
    #CUBE ONE
    drawFront(-30, 30, CUBE_SIZE, CUBE_ONE_COLOR)
    drawTop(-60, 60, 2 * CUBE_SIZE, CUBE_ONE_COLOR)

    #CUBE TWO
    drawFront(-30, -30, CUBE_SIZE, CUBE_TWO_COLOR)

    #CUBE THREE
    drawFront(30, -30, CUBE_SIZE, CUBE_THREE_COLOR)
    drawRight(60, 0, 2 * CUBE_SIZE, CUBE_THREE_COLOR)

    #CUBE FOUR
    drawFront(30, 30, CUBE_SIZE, CUBE_FOUR_COLOR)
    drawTop(0, 60, 2 * CUBE_SIZE, CUBE_FOUR_COLOR)
    drawRight(60, 60, 2 * CUBE_SIZE, CUBE_FOUR_COLOR)

    #CUBE FIVE
    drawTop(-60 + (60 * math.sin(math.pi/4)), 60 + (60 * math.sin(math.pi/4)), 2 * CUBE_SIZE, CUBE_FIVE_COLOR)

    #CUBE SIX
    drawTop(0 + (60 * math.sin(math.pi/4)), 60 + (60 * math.sin(math.pi/4)), 2 * CUBE_SIZE, CUBE_SIX_COLOR)
    drawRight(60 + (60 * math.sin(math.pi/4)), 60 + (60 * math.sin(math.pi/4)), 2 * CUBE_SIZE, CUBE_SIX_COLOR)

    #CUBE SEVEN
    drawRight(60 + (60 * math.sin(math.pi/4)), 0 + (60 * math.sin(math.pi/4)), 2 * CUBE_SIZE, CUBE_SEVEN_COLOR)



def drawFront(x, y, size, color):
    t.penup()
    t.fillcolor(color)

    t.goto(x - size, y + size)
    t.pendown()
    t.begin_fill()

    t.goto(x + size, y + size)
    t.goto(x + size, y - size)
    t.goto(x - size, y - size)
    t.goto(x - size, y + size)

    t.end_fill()
    t.penup()

def drawTop(x0, y0, size, color):
    t.penup()
    t.fillcolor(color)

    t.goto(x0, y0)
    t.pendown()
    t.begin_fill()

    adjustedSize = size * math.sin(math.pi/4)

    t.goto(x0 + adjustedSize, y0 + adjustedSize)
    t.goto(x0 + adjustedSize + size, y0 + adjustedSize)
    t.goto(x0 + size, y0)
    t.goto(x0, y0)

    t.end_fill()
    t.penup()

def drawRight(x0, y0, size, color):
    t.penup()
    t.fillcolor(color)

    t.goto(x0, y0)
    t.pendown()
    t.begin_fill()

    adjustedSize = size * math.sin(math.pi/4)

    t.goto(x0 + adjustedSize, y0 + adjustedSize)
    t.goto(x0 + adjustedSize, y0 + adjustedSize - size)
    t.goto(x0, y0 - size)
    t.goto(x0, y0)

    t.end_fill()
    t.penup()



turtle.setup(SCREEN_SIZE, SCREEN_SIZE)
s = turtle.Screen()
s.colormode(255)

t = turtle.Turtle(visible = "false")
t.hideturtle()
turtle.tracer(0, 0)

drawCubes()

turtle.done()