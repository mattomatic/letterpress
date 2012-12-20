import Image
import ImageFilter
import ImageChops
import sys

img = Image.open(sys.argv[1])

colors = [
    (122, 201, 244, 'b'),
    (230, 229, 226, 'w'),
    (246, 155, 144, 'r'),
    (255, 72, 53, 'r'),
    (8, 164, 254, 'b'),
    (0, 0, 0, 'black')]

letters = [chr(c + 97) for c in range(26)]
alphas = {c: Image.open('alphabet/%s.png' % c) for c in letters}
 
def color(pixel):
    ccmp = lambda x: (abs(pixel[0] - x[0]) + 
                      abs(pixel[1] - x[1]) + 
                      abs(pixel[2] - x[2]))
    colors.sort(key=ccmp)
    return colors[0][3]

def imgsum(img):
    total = 0
    for x in range(img.size[0]):
        for y in range(img.size[1]):
            pxl = img.getpixel((x, y))
            total += pxl[0] + pxl[1] + pxl[2]
    return total

def letter(tile):
    items = alphas.items()
    items.sort(key=lambda x: imgsum(ImageChops.difference(x[1], tile)))
    return items[0][0]

def grayscale(tile):
    tile = tile.copy()
    for x in range(tile.size[0]): 
        for y in range(tile.size[1]):
            if color(tile.getpixel((x, y))) != 'black':
                tile.putpixel((x, y), (255, 255, 255))

    return tile

def coords(row, col):
    return (col * 128, 
            320 + row * 128, 
            col * 128 + 128, 
            320 + row * 128 + 128)

for row in range(5):
    for col in range(5):
        tile = img.crop(coords(row, col))
        colr = color(tile.getpixel((5,5)))
        tile = grayscale(tile)
        lttr = letter(tile)
        print '%s=%s' % (lttr, colr),
    print
