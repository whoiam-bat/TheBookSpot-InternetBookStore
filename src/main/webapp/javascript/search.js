function arrayInit() {
    const xhttp = new XMLHttpRequest();
    let res = [];
    xhttp.onreadystatechange = function () {
        if(this.readyState === 4 && this.status === 200) {
            let xmlDoc = this.responseXML;
            let tempList = xmlDoc.getElementsByTagName("book");

            for (let i = 0; i < tempList.length; i++) {
                res.push(tempList[i].getElementsByTagName("title")[0].childNodes[0].nodeValue);
            }
        }
    };
    xhttp.open("GET", "xml/books-out.xml", true);
    xhttp.send();

    return res;
}

let booksInStock = arrayInit();

function autocomplete(inp, booksInStock) {

    var currentFocus;

    //execute a function when someone writes in the text field
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;

        //close any already open list of autocompleted values
        closeAllLists();

        if (!val) {
            return false;
        }
        currentFocus = -1;

        //create DIV element that will contain the items (values)

        a = document.createElement("DIV");
        a.setAttribute("id", "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");

        //append the DIV element as a child of the autocomplete container
        this.parentNode.appendChild(a);

        for (const it of booksInStock) {
            //check if the item starts with the same letters as the text field value
            if (it.substr(0, val.length).toUpperCase() === val.toUpperCase()) {
                //create a DIV element for each matching element
                b = document.createElement("DIV");
                b.setAttribute("id", "item");
                b.setAttribute("class", "item");
                //make the matching letters bold
                b.innerHTML = "<strong>" + it.substr(0, val.length) + "</strong>";
                b.innerHTML += it.substr(val.length);

                // insert a input field that will hold the current array item's value
                b.innerHTML += "<input type='hidden' value='" + it + "'>";

                // execute a function when someone clicks on the item value (DIV element)
                b.addEventListener("click", function (e) {
                    // insert the value for the autocomplete text field:
                    inp.value = this.getElementsByTagName("input")[0].value;
                    /* close the list of autocompleted values,
                    (or any other open lists of autocompleted values*/
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });

    // execute a function presses a key on the keyboard
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode === 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable*/
            currentFocus++;
            //and make the current item more visible
            addActive(x);
        } else if (e.keyCode === 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable*/
            currentFocus--;
            //and and make the current item more visible
            addActive(x);
        } else if (e.keyCode === 13) {
            //If the ENTER key is pressed, prevent the form from being submitted
            e.preventDefault();
            if (currentFocus > -1) {
                //and simulate a click on the "active" item
                if (x) x[currentFocus].click();
            }
        }
    });

    function addActive(x) {
        //a function to classify an item as active
        if (!x) return false;
        //start by removing the "active" class on all items
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        //add class "autocomplete-active"
        x[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(x) {
        //a function to remove the "active" class from all autocomplete items:
        for (const iter of x) {
            iter.classList.remove("autocomplete-active");
        }
    }

    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (const iter of x) {
            if (elmnt !== iter && elmnt !== inp) {
                iter.parentNode.removeChild(iter);
            }
        }
    }

    document.addEventListener("click", function(e) {
        closeAllLists(e.target);
    });
}

  /*initiate the autocomplete function on the "myInput" element,
    and pass along the countries array as possible autocomplete values*/

autocomplete(document.getElementById("search-field"), booksInStock);
