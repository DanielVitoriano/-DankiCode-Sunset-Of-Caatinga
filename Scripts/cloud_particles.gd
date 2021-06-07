extends StaticBody2D

var life = 200
var move = Vector2()

# Called when the node enters the scene tree for the first time.
func _ready():
	randomize()
	move = Vector2(rand_range(-0.7, 0.7), rand_range(0, 3))

func _process(_delta):
	life -= 1
	translate(move)

	if life <= 0:
		queue_free()
