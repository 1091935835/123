Rayon[] rayons; // déclare un tableau d'objets de type "Rayon" nommé "rayons"
int nombre=96; // nombre de rayons
void setup() {
  size(400, 400); 
  colorMode(HSB);
  rayons = new Rayon[0]; // initialise le tableau rayons (un seul élément pour l'instant)
  for (int a=0; a<nombre; a++) {
    new Rayon(TWO_PI/nombre*a);
  }
  background(0);
  fill(0, 20); // remplissage noir presque invisible grâce à la valeur alpha
}

void draw() { 
  noStroke();
	strokeWeight(2);
  rect(0, 0, width, height); // les rectangles sont presques invisibles (alpha à 10) pour créer un effet de fondu
  translate(width/2, height/2); // décale l'origine au centre de la fenêtre
  for (Rayon r : rayons) { // pour tous les éléments "r" de type "Rayon" compris dans le tableau "rayons"
    r.dessine();  // appelle la fonction dessine()
  }
}

class Rayon {
  float angle, vortex, max;
  float[] rayz;
  color c;

  Rayon(float an) {
    angle= an; // angle du rayon par rapport à l'axe des abscisses
    vortex=0.003; //random(-0.005, 0.005); // décalage de l'angle créant un effet de vortex
    max= random(50, 400); // longueur max du rayon
    rayz = new float[0];
    c =  color(random(255), 250, 255);
    rayons=(Rayon[]) append(rayons, this); // augmente le tableau "rayons" d'un élément de type Rayon ayant pour valeur "this"
    // append(nomDuTableau, valeurDeL'élément)
    // la valeur "this" renvoie à l'objet actif et dépend du contexte dans lequel on l'utilise
  }

  void dessine() {
    angle+=vortex;
    stroke(c);
    float[] nouveauRayz=new float[0];
    if (random(5)<1) {  // effectuer l'instruction suivante pour 10% des cas
      nouveauRayz=(float[]) append(nouveauRayz, random(1));
    }
    for (float f : rayz) {
      f+=2.0;
      point(cos(angle+vortex)*f, sin(angle+vortex)*f);
      if (f<max) {
        nouveauRayz=(float[]) append(nouveauRayz, f);
      }
    }
    rayz=nouveauRayz;
  }
}
