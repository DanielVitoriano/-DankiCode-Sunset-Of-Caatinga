extends AnimatedSprite

func _ready():
	playing = true

func _on_area_body_entered(_body):
	queue_free()
