package fr.istic.prg1.tree;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

import java.util.Scanner;

import static fr.istic.prg1.tree_util.NodeType.DOUBLE;
import static fr.istic.prg1.tree_util.NodeType.LEAF;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
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
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
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
			//1er cas : si y cherch� est plus grand que la valeur y au milieu, on fait un goRight()
			if(y>baseY) {
				it.goRight();
				//remettre baseY au milieu. example: Si on va a droit, y start sera 128 et y end sera 255. Donc pour mettre baseY au milieu
				//on doit le changer comme �a:
				baseY = (baseY/2)+127;
				//mettre le bool cutByY � true pour dire que ici on a fait un d�coupage horizontal.
				cutByY = true;
			} else {
				//2eme cas : else on fait un goLeft()
				it.goLeft();
				//if cutByY veut dire que si notre noeud actuel est � droit du noeud racine
				if (cutByY) {
					baseY = ((baseY - 127) / 2) + 127;
				}else {
					baseY = baseY/2;
				}
			}
			//Si nodeType est LEAF on faire un return tout de suite
			if(it.nodeType()!= LEAF) {
				//m�me chose, si x cherch� plus grand que baseX actuel
				if(x>baseX) {
					it.goRight();
					baseX = (baseX/2)+127; //remettre valeur de baseX au milieu et mettre cutByX � true
					cutByX = true;
				} else {
					//Si x cherch� est inf�rieur ou �gale que baseX actuel
					it.goLeft();
					//Si le noeud actuel est d�j� � droite apr�s la premi�re d�coupage verticale
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
         * this devient identique à image2.
         *
         * @param image2
         *            image à copier
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
		//Si it�rateur de image2 est plac� � un node DOUBLE
		if(it2.nodeType() == DOUBLE) {
			//if it�rateur contient rien (pas de valeur d�fini avant)
			if(it.isEmpty()) {
				it.addValue(Node.valueOf(it2.getValue().state));
			} else {
				//Si it�rateur a d�j� une state, on le changera � la state de it2 dans la m�me position
				it.setValue(Node.valueOf(it2.getValue().state));
			}
			//cf cours, m�me d�marche pour parcourir l'arbre
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
		//Si c'est un LEAF, �a veut dire que tout ce qui apr�s dans this n'est plus important, il doit prendre
		//la m�me valeur que image2
		else if(it2.nodeType() == LEAF) {
			it.clear();
			it.addValue(Node.valueOf(it2.getValue().state));
		}
	}




	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {

	}

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
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
		System.out.println("Fonction non demeand�e");
		System.out.println("-------------------------------------------------");
		System.out.println();	    
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
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
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
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
	 *         sont allumés dans this, false sinon
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
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	    return false;
	}

}
