package fr.istic.prg1.tree;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

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
		Iterator<Node> it2 = image2.iterator();
		Iterator<Node> it = this.iterator();

		//Clear this image
		it.clear();

		if (!it2.isEmpty())
			rotateAux(it2, it);
	}
	private void rotateAux (Iterator<Node> it2, Iterator<Node> it) {
		if (!it2.isEmpty()) {
			int e = it2.getValue().state;
			it.addValue(Node.valueOf(e));
			it2.goLeft();
			it.goRight();
			rotateAux(it2, it);
			it2.goUp();
			it.goUp();
			it2.goRight();
			it.goLeft();
			rotateAux(it2, it);
			it2.goUp();
			it.goUp();
		}
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
		Iterator<Node> it = this.iterator();
		videoInverseAux(it);
	}
	public void videoInverseAux(Iterator<Node> it){
		if (!it.isEmpty()) {
			Node ns = it.getValue();

			//Process this one
			if (ns.state == 1) {
				it.setValue(Node.valueOf(0));
			}
			else if (ns.state == 0) {
				it.setValue(Node.valueOf(1));
			}

			//Process the sons
			it.goLeft();
			videoInverseAux(it);
			it.goUp();
			it.goRight();
			videoInverseAux(it);
			it.goUp();
		}
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
		Iterator<Node> it = iterator();
		if (!it.isEmpty())
			it.clear();
		mirrorVAux(it, image2.iterator(), true);
	}

	void mirrorVAux(Iterator<Node> it, Iterator<Node> it1, boolean bool){
		NodeType otype = it1.nodeType();
		assert otype == NodeType.LEAF || otype == NodeType.DOUBLE : "l'arbre comporte des noeuds simples";
		switch (otype) {
			case LEAF:
				it.addValue(it1.getValue());
				break;
			case DOUBLE:
				it.addValue(Node.valueOf(2));
				it.goLeft();
				if (bool) it1.goRight();
				else it1.goLeft();
				mirrorVAux(it, it1, !bool);
				it.goUp(); it1.goUp();
				it.goRight();
				if (bool) it1.goLeft();
				else it1.goRight();
				mirrorVAux(it, it1, !bool);
				it.goUp(); it1.goUp();
				break;
		}
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
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		zoomInAux(it,it2);
	}
	private void zoomInAux(Iterator<Node> it, Iterator<Node> it2){
		if (it2.getValue().equals(Node.valueOf(0))) it.addValue(Node.valueOf(0));
		else if (it2.getValue().equals(Node.valueOf(1))) it.addValue(Node.valueOf(1));
		else {
			it2.goLeft();
			if (it2.getValue().equals(Node.valueOf(0))) it.addValue(Node.valueOf(0));
			else if (it2.getValue().equals(Node.valueOf(1))) it.addValue(Node.valueOf(1));
			it2.goLeft();
			affectAux(it, it2);
		}
	}
	/*
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
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		/*if(!it.isEmpty()) {
			it.setValue(Node.valueOf(2));
			it.goRight();
			it.addValue(Node.valueOf(0));
			it.goUp();
			it.goLeft();
			it.addValue(Node.valueOf(2));
			it.goRight();
			it.addValue(Node.valueOf(0));
			it.goUp();
			it.goLeft();
			zoomOutAux(it, it2);
		}*/
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();
		it.addValue(Node.valueOf(1));
		//zoomOutAux(it, it2);
		/*if(!it2.isEmpty()) {
			if (it2.getValue().equals(Node.valueOf(0))) {
				if (it.isEmpty()) it.addValue(Node.valueOf(0));
				else it.setValue(Node.valueOf(0));
			} else if (it2.getValue().equals(Node.valueOf(1))) {
				if (it.isEmpty()) it.addValue(Node.valueOf(1));
				else it.setValue(Node.valueOf(1));
			} else {
				if (it.isEmpty()) it.addValue(Node.valueOf(2));
				else it.setValue(Node.valueOf(2));
				affectAux(it, it2);
			}
		}*/
		affectAux(it,it2);
	}
	private void zoomOutAux(Iterator<Node> it, Iterator<Node> it2){
		if(!it2.isEmpty()) {
			if (it2.getValue().equals(Node.valueOf(0))){
				if(it.isEmpty()) it.addValue(Node.valueOf(0));
				else it.setValue(Node.valueOf(0));
			}
			else if (it2.getValue().equals(Node.valueOf(1))){
				if(it.isEmpty()) it.addValue(Node.valueOf(1));
				else it.setValue(Node.valueOf(1));
			}
			else {
				if(it.isEmpty()) it.addValue(Node.valueOf(2));
				else it.setValue(Node.valueOf(2));
				//it.addValue(Node.valueOf(2));
				it2.goLeft();
				it.goLeft();
				zoomOutAux(it, it2);
				it2.goUp();
				it.goUp();
				it2.goRight();
				it.goRight();
				zoomOutAux(it, it2);
				it2.goUp();
				it.goUp();
			}
		}
		/*if(!it2.isEmpty()){
			affectAux(it,it2);
			it.goRoot();
		}*/
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
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		if (!it1.isEmpty() && !it2.isEmpty()) {
			this.auxUnion(it, it1, it2);
		}
	}

	private void auxUnion(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		if (!it1.isEmpty() && !it2.isEmpty()) {
			/*int e = it1.getValue().state + it2.getValue().state;
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
					if(it.isEmpty()) it.addValue(Node.valueOf(2));
					else it.setValue(Node.valueOf(2));
					//it.addValue(Node.valueOf(2));
					it.goLeft();
					it1.goLeft();
					it2.goLeft();
					auxUnion(it, it1, it2);
					int left = it.getValue().state;
					if (left != 2) {
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
					if (right != 2) {
						it.clear();
						it.addValue(Node.valueOf(right));
					}
					break;

				default:
					it.addValue(Node.valueOf(0));
					break;
			}*/
			if(it1.getValue().equals(Node.valueOf(0))&&it2.getValue().equals(Node.valueOf(0))){
				it.addValue(Node.valueOf(0));
			}
			else if(it1.getValue().equals(Node.valueOf(1))||it2.getValue().equals(Node.valueOf(1))){
				it.addValue(Node.valueOf(0));
			}
			else if(it1.getValue().equals(Node.valueOf(0))||it2.getValue().equals(Node.valueOf(2))){
				affectAux(it, it2);
			}
			else if(it1.getValue().equals(Node.valueOf(2))||it2.getValue().equals(Node.valueOf(0))){
				affectAux(it, it1);
			}
			else {
				it.addValue(Node.valueOf(2));
				it.goLeft();
				it1.goLeft();
				it2.goLeft();
				auxUnion(it, it1, it2);
				int left = it.getValue().state;
				if (left != 2) {
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
				if (right != 2) {
					it.clear();
					it.addValue(Node.valueOf(right));
				}
			}
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
		Iterator<Node> it = this.iterator();
		if(!it.isEmpty()){
			return testDiagonalAux(it);
		}
		else return false;
	}
	private boolean testDiagonalAux(Iterator<Node> it) {
		boolean result = true;
		/*if(it.getValue().equals(Node.valueOf(0))) result = false;
		else if(it.getValue().equals(Node.valueOf(1))) result = true;
		else {
			it.goLeft();
			boolean cond1 = testDiagonalAux(it);
			it.goUp();
			it.goRight();
			boolean cond2 = testDiagonalAux(it);
			result = cond1&&cond2;
		}*/
		if (!it.isEmpty()) {
			if (it.getValue().equals(Node.valueOf(0))) {
				result = false;
			}

			else if (it.getValue().equals(Node.valueOf(2))) {

				//Go to Left 1st generation
				it.goLeft();

				if (it.getValue().equals(Node.valueOf(1))) {
					//Go Up 1st generation
					it.goUp();
					//Go to Right 1st generation
					it.goRight();
					if (it.getValue().equals(Node.valueOf(0))) {
						result = false;
					} else if (it.getValue().equals(Node.valueOf(2))) {
						// go to Right 2nd generation
						it.goRight();
						if (!testDiagonalAux(it)) {
							result = false;
						}
						it.goUp();
					}
					else result = true;
				} else if (it.getValue().equals(Node.valueOf(0))) {
					result = false;
				} else {
					it.goLeft();
					if (!testDiagonalAux(it)) {
						result = false;
					}
					it.goUp();
				}
				it.goUp();
			}
		}
		return result;
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
		Iterator<Node> itThis = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		if (it2.isEmpty()) {
			return false;
		} else {
			return isIncludedAux(itThis, it2);
		}
	}

	private boolean isIncludedAux (Iterator<Node> itThis, Iterator<Node> it2) {
		boolean result = true;
		if (!it2.isEmpty()) {
			if (!itThis.isEmpty()) {
				if (it2.getValue().equals(Node.valueOf(1)) || itThis.getValue().equals(Node.valueOf(0))) {
					//On ne fait rien
				} else if (it2.getValue().equals(Node.valueOf(0)) || itThis.getValue().equals(Node.valueOf(1))) {
					result = false;
				} else {
					itThis.goLeft();
					it2.goLeft();
					if (isIncludedAux(itThis, it2)) {
						itThis.goUp();
						it2.goUp();
						itThis.goRight();
						it2.goRight();
						if (!isIncludedAux(itThis, it2)) {
							result = false;
						}
					} else {
						result = false;
					}
					itThis.goUp();
					it2.goUp();
				}
			}
		}
		return result;
	}

}
