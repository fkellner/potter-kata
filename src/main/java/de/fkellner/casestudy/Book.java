package de.fkellner.casestudy;

public class Book implements Item {
    private int priceInCents;
    private String title;
    private String isbn;

    public Book(String isbn, String title, int priceInCents) {
        this.isbn = isbn;
        this.title = title;
        this.priceInCents = priceInCents;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
	public int hashCode() {
		return isbn.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return this.getIsbn() == ((Book)obj).getIsbn();
	}
}
