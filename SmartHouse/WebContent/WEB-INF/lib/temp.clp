;;;;;;;;;;;;;; templates ;;;;;;;;;;;;;;;;;;;;;;;;;

(deftemplate decyzja "Podjêcie decyzji zwi¹zanej z roletami"
	(slot stan-rolet)
	(slot temperatura)
 )
 
 (deftemplate akcja "Jakie dzia³ania podjêto"
 	(slot wartosc)
 )
 
;;;;;;;;;;;;;; facts ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;(deffacts decyzje "Mo¿liwe przypadki zdarzeñ"
;;	(decyzja (stan-rolet zasloniete) ( wartosc 24))
;;	(decyzja (stan-rolet zasloniete) ( wartosc 26))
;;	(decyzja (stan-rolet odsloniete) ( wartosc 24))
;;	(decyzja (stan-rolet odsloniete) ( wartosc 26))
;; )

;;;;;;;;;;;;;; rules ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defrule decyzja1
	(declare (salience -1 ))
	(decyzja (stan-rolet z) (temperatura ?temp))
	(test(>= ?temp 25))
 =>
	(assert (akcja (wartosc nie-rob-nic)))
)

(defrule decyzja2
	(declare (salience -2 ))
	(decyzja (stan-rolet o) (temperatura ?temp))
	(test(>= ?temp 25))
 =>
	(assert (akcja (wartosc zaslon-rolety)))
)

(defrule decyzja3
	(declare (salience -3 ))
	(decyzja (stan-rolet z) (temperatura ?temp))
	(test(< ?temp 25))
 =>
	(assert (akcja (wartosc odslon-rolety)))
)

(defrule decyzja4
	(declare (salience -4 ))
	(decyzja (stan-rolet o) (temperatura ?temp))
	(test(< ?temp 25))
 =>
	(assert (akcja (wartosc nie-rob-nic)))
)









