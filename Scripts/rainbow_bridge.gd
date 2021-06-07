extends StaticBody2D

var player


# Called when the node enters the scene tree for the first time.
func _ready():
	pass

#func _process(_delta):
#	pass

func _on_brilinho_body_entered(body):
	body.Brilinho(true)
	print(body)


func _on_brilinho_body_exited(body):
	body.Brilinho(false)
