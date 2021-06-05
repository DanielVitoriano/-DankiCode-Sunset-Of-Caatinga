extends StaticBody2D

var life = 200

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.

func _process(_delta):
	print(life)
	life -= 1
	global_position.y += 1

	if life <= 0:
		queue_free()
