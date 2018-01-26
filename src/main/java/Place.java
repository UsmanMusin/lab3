
public class Place {
  private long code;
  private String status;
  private String name;

  Place(long code, String status, String name){
      this.code = code;
      this.name = name;
      this.status = status;
  }

  public String getStatus(){
      return status;
  }

  public String getName(){
      return name;
  }

  public long getCode(){ return code;}

  @Override
    public String toString(){
      return Long.toString(code)+" "+status+" "+name;
  }

  @Override
  public boolean equals(Object place){
      if(this==place) {
          return true;
      }
      if (place == null || place.getClass() != this.getClass()) {
          return false;
      }
      Place place1 = (Place) place;
      if((this.code == place1.getCode()) && (this.status.equals(place1.getStatus()))
              && (this.name.equals(place1.getName()))){
          return true;
      }
        return false;
  }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.getName() == null ? 0 : this.getName().hashCode());
        result = prime * result + (this.getStatus() == null ? 0 : this.getStatus().hashCode());
        result = prime * result + (int)this.getCode();
        return result;
    }
}
