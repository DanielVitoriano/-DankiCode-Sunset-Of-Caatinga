extends KinematicBody2D

#variáveis de status
var life = 3;

#variáveis de física
var run_speed = 300
var dir_fish = Vector2(1, 0)
var rot_fish = true
#demais
var can_move = true
var can_fire = true
var anim_index = 0 # 0 parado; 2 pulando

#preloads
var pre_fish = preload("res://Scenes/bullet_fish.tscn")

var velocity = Vector2()

func animation():
	if anim_index == 0: #parado
		$AnimatedSprite.play("idle")
		
	elif anim_index == 1: #andando
		pass
		
	elif anim_index == 2: #pulando
		$AnimatedSprite.play("jump")

func Move():
	if can_move:
		velocity = Vector2(0 ,0)
		var right = Input.is_action_pressed('ui_right')
		var left = Input.is_action_pressed('ui_left')
		var up = Input.is_action_pressed("ui_up")
		var down = Input.is_action_pressed("ui_down")
		#var jump = Input.is_action_just_pressed('ui_up')

		if up:
			velocity.y -= run_speed
			
		elif down:
			velocity.y += run_speed

		if right:
			dir_fish = Vector2(1, 0)
			rot_fish = true
			velocity.x += run_speed
			$AnimatedSprite.flip_h = false
			#$cat.flip_h = false
			$collision.position.x = 10
			$pos_fish.position.x = 50
			$pos_fish.rotation = 0
			
		elif left:
			dir_fish = Vector2(-1 , 0)
			rot_fish = false
			velocity.x -= run_speed
			$AnimatedSprite.flip_h = true
			#$cat.flip_h = true
			$collision.position.x = -10
			$pos_fish.position.x = -50
			$pos_fish.rotation = - 180
		
		if velocity == Vector2(0, 0):
			anim_index = 0
		else:
			anim_index = 1

func _physics_process(delta):
	
	animation()
	Move()
	Fire()
	velocity = move_and_slide(velocity, Vector2.UP)

func Fire():
	print(get_tree().get_nodes_in_group("fish_player").size())
	if Input.is_action_just_pressed("ui_fire") and can_fire and get_tree().get_nodes_in_group("fish_player").size() < 3:
		var fish = pre_fish.instance()
		fish.global_position = $pos_fish.global_position
		fish.dir = dir_fish
		fish.FlipFish(rot_fish)
		fish.add_to_group("fish_player")
		get_parent().add_child(fish)

func play_on_enter():
	#$anim.stop()
	#$anim.play("on_enter")
	pass

