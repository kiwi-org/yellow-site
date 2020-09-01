function loadButton() {
    let buttonStyle = [["button-primary", "button-caution", "button-action", "button-highlight", "button-royal"], ["button-pill", "button-rounded"], ["button-small", "button-tiny", "button-jumbo", "button-large", "button-giant"]];
    document.querySelectorAll("a.button").forEach(e => {
        buttonStyle.forEach(item => {
            e.classList.add(item[Math.floor(Math.random() * item.length)]);
        })
    })
}

window.onload = loadButton;