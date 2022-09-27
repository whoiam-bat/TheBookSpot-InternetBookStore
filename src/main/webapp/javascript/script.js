window.onscroll = () => {
    if(window.scrollY > 80) {
        document.querySelector(".header .header2").classList.add('active');
    } else {
        document.querySelector(".header .header2").classList.remove('active');
    }
}
window.load = () => {
    if(window.scrollY > 80) {
        document.querySelector(".header .header2").classList.add('active');
    } else {
        document.querySelector(".header .header2").classList.remove('active');
    }
}