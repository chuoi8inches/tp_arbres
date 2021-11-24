package fr.istic.prg1.tree;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

import java.util.Scanner;

import static fr.istic.prg1.tree_util.NodeType.DOUBLE;
import static fr.istic.prg1.tree_util.NodeType.LEAF;

/**
 * @author MickaÃ«l Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe dÃ©crivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnÃ©e du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumÃ© dans this, false sinon
	 */

	@Override
	public boolean isPixelOn(int x, int y) {
		int baseX = 127;
		int baseY = 127;
		boolean cutByX = false;
		boolean cutByY = false;

		Iterator<Node> it=this.iterator();
		//Si nodeType est LEAF on faire un return tout de suite
		while(it.nodeType()!= LEAF) {
			//1er cas : si y cherché est plus grand que la valeur y au milieu, on fait un goRight()
			if(y>baseY) {
				it.goRight();
				//remettre baseY au milieu. example: Si on va a droit, y start sera 128 et y end sera 255. Donc pour mettre baseY au milieu
				//on doit le changer comme ça:
				baseY = (baseY/2)+127;
				//mettre le bool cutByY à true pour dire que ici on a fait un découpage horizontal.
				cutByY = true;
			} else {
				//2eme cas : else on fait un goLeft()
				it.goLeft();
				//if cutByY veut dire que si notre noeud actuel est à droit du noeud racine
				if (cutByY) {
					baseY = ((baseY - 127) / 2) + 127;
				}else {
					baseY = baseY/2;
				}
			}
			//Si nodeType est LEAF on faire un return tout de suite
			if(it.nodeType()!= LEAF) {
				//même chose, si x cherché plus grand que baseX actuel
				if(x>baseX) {
					it.goRight();
					baseX = (baseX/2)+127; //remettre valeur de baseX au milieu et mettre cutByX à true
					cutByX = true;
				} else {
					//Si x cherché est inférieur ou égale que baseX actuel
					it.goLeft();
					//Si le noeud actuel est déjà à droite après la première découpage verticale
					if (cutByX){
						baseX=((baseX-127)/2)+127;
					}else {
						baseX = baseX/2;
					}
				}
			}
		}
		return it.getValue().equals(Node.valueOf(1));
	}

	/**
         * this devient identique Ã  image2.
         *
         * @param image2
         *            image Ã  copier
         *
         * @pre !image2.isEmpty()
         */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it=this.iterator();
		Iterator<Node> it2=image2.iterator();
		affectAux(it,it2);
	}

	public void affectAux(Iterator<Node> it, Iterator<Node> it2) {
		//Si itérateur de image2 est placé à un node DOUBLE
		if(it2.nodeType() == DOUBLE) {
			//if itérateur contient rien (pas de valeur défini avant)
			if(it.isEmpty()) {
				it.addValue(Node.valueOf(it2.getValue().state));
			} else {
				//Si itérateur a déjà une state, on le changera à la state de it2 dans la même position
				it.setValue(Node.valueOf(it2.getValue().state));
			}
			//cf cours, même démarche pour parcourir l'arbre
			it.goLeft();
			it2.goLeft();
			affectAux(it,it2);
			it.goUp();
			it2.goUp();
			it.goRight();
			it2.goRight();
			affectAux(it,it2);
			it.goUp();
			it2.goUp();
		}
		//Si c'est un LEAF, ça veut dire que tout ce qui après dans this n'est plus important, il doit prendre
		//la même valeur que image2
		else if(it2.nodeType() == LEAF) {
			it.clear();
			it.addValue(Node.valueOf(it2.getValue().state));
		}
	}




	/**
	 * this devient rotation de image2 Ã  180 degrÃ©s.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {

	}

	/**
	 * this devient rotation de image2 Ã  90 degrÃ©s dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demeandée");
		System.out.println("-------------------------------------------------");
		System.out.println();	    
	}

	/**
	 * this devient inverse vidÃ©o de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image Ã  agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image Ã  agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient quart supÃ©rieur gauche de image2.
	 *
	 * @param image2
	 *            image Ã  agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Le quart supÃ©rieur gauche de this devient image2, le reste de this
	 * devient Ã©teint.
	 * 
	 * @param image2
	 *            image Ã  rÃ©duire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumÃ©s.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumÃ©s.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		it.clear();
		this.auxUnion(it, image1.iterator(), image2.iterator());
	}

	private void auxUnion(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		int e = it1.getValue().state + it2.getValue().state;
		switch (e) {
			case 2:
				if (it1.getValue().state == 0) {
					affectAux(it, it2);
				} else if (it1.getValue().state == 2) {
					affectAux(it, it1);
				} else {
					it.addValue(Node.valueOf(1));
				}
				break;

			case 3:
				it.addValue(Node.valueOf(1));
				break;

			case 4:
				it.addValue(Node.valueOf(2));
				it.goLeft();
				it1.goLeft();
				it2.goLeft();
				auxUnion(it, it1, it2);
				int left = it.getValue().state;
				if ( left != 2) {
					it.clear();
					it.addValue(Node.valueOf(left));
				}
				it.goUp();
				it1.goUp();
				it2.goUp();
				it.goRight();
				it1.goRight();
				it2.goRight();
				auxUnion(it, it1, it2);
				int right = it.getValue().state;
				it.goUp();
				it1.goUp();
				it2.goUp();
				if ( right != 2) {
					it.clear();
					it.addValue(Node.valueOf(right));
				}
				break;

			default:
				it.addValue(Node.valueOf(e));
				break;
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumÃ©s dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		boolean res = false;

		return true;
	}

	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnÃ©e du premier point
	 * @param x2
	 *            abscisse du deuxiÃ¨me point
	 * @param y2
	 *            ordonnÃ©e du deuxiÃ¨me point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont reprÃ©sentÃ©s par
	 *         la mÃªme feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumÃ©s
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	    return false;
	}

}
