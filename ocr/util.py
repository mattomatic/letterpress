import Image
import ImageFilter
import sys
import glob

img = Image.open(sys.argv[1])

colors = [
    (122, 201, 244, 'lb'),
    (230, 229, 226, 'w'),
    (246, 155, 144, 'lr'),
    (255, 72, 53, 'dr'),
    (8, 164, 254, 'db'),
    (0, 0, 0, 'b')]

chars = [chr(c + 97) for c in range(26)]
alphas = {c: Image.open('alphabet/%s.png' % c) for c in chars}
print alphas
 
def color(pixel):
    ccmp = lambda x: (abs(pixel[0] - x[0]) + 
                      abs(pixel[1] - x[1]) + 
                      abs(pixel[2] - x[2]))
    colors.sort(key=ccmp)
    return colors[0][3]

def grayscale(tile):
    X, Y = tile.size
    
    for x in range(X): 
        for y in range(Y):
            if color(tile.getpixel((x, y))) != 'b':
                tile.putpixel((x, y), (255, 255, 255))

    return tile

def coords(row, col):
    return (row * 128, 
            320 + col * 128, 
            row * 128 + 128, 
            320 + col * 128 + 128)

for row in range(5):
    for col in range(5):
        tile = img.crop(coords(row, col))
        colr = color(tile.getpixel((5,5)))
        tile.save('current/%s-%s.png' % (row, col))     
