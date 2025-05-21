//
//
// Name:    Murillo, Clarissa
// Project: 5
// Due:     5/12/23
// Course:  cs-2400-03-sp23
//
// Description:
//           This interface extends List Interface
//

import java.util.Iterator;
public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T> {
    public Iterator<T> getIterator();
} // end ListWithIteratorInterface