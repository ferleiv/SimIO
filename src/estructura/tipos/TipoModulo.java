package estructura.tipos;

public enum TipoModulo {
    CPU("CPU"),
    IO("IO"),;

    private String name;

    TipoModulo(String name) { this.name = name; }

    public String getName() { return name; }
}
