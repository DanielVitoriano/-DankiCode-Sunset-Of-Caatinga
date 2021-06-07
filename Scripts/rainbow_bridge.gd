extends StaticBody2D

var player


# Called when the node enters the scene tree for the first time.
func _ready():
	pass

#func _process(_delta):
#	pass

func _on_brilinho_body_entered(_body):
	$brilinho_part.emitting = true
	pass


func _on_brilinho_body_exited(_body):
	$brilinho_part.emitting = false
	pass
