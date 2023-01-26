// Bugrer functional
const bugrer = document.querySelector(".burger");
const burgerActive = () => {
	if (bugrer) {
		const menu = document.querySelector(".navbar");
		bugrer.addEventListener("click", function(e) {
			document.body.classList.toggle("_lock");
			menu.classList.toggle("_active");
		});
	}
};
burgerActive();

// Change the active photo on the page of selected product
const imagesItems = Array.from(document.querySelectorAll(".product__row-img"));
const tabsItems = document.querySelectorAll(".product-left-side-tab");
const clearActiveClass = (element, classname = "is-active") => {
	element.forEach(item => item.classList.remove(`${classname}`));
}
const setActiveClass = (element, index, classname = "is-active") => {
	element[index].classList.add(`${classname}`);
}
const checkOutTabs = (item, index) => {
	item.addEventListener("click", () => {
		if (item.classList.contains("is-active")) return;
		clearActiveClass(tabsItems);
		clearActiveClass(imagesItems);
		setActiveClass(tabsItems, index);
		setActiveClass(imagesItems, index);
	});
}

tabsItems.forEach(checkOutTabs);

// Functional of set rating of product in the form for create comment
const stars = Array.from(document.getElementsByClassName('star-element'));
const paintStars = (item, index) => {
	item.addEventListener("click", () => {
		for (var i = 0; i <= index; i++) {
			stars[i].classList.remove("_checked-star");
			stars[i].classList.add("_checked-star");
		}
		for (var i = index + 1; i <= 4; i++) {
			stars[i].classList.remove("_checked-star");
		}
	});
};
stars.forEach(paintStars);

// Show more comments
const showMoreButton = Array.from(document.getElementsByClassName("comments__show-more"));
const commentsByUsers = Array.from(document.getElementsByClassName("comment-by-user"));

const showComments = (items) => {
	var length = items.length;
	if (items.length <= 3) {
		showMoreButton[0].classList.add("_hidden");
	}
	for (var i = 0; i < 3; i++) {
		items[i].classList.add("_show-comment");
	}
};
showComments(commentsByUsers);

// Functional of button `Show more` in the comments
const pressShowMore = (button) => {
	button.addEventListener("click", () => {
		const indexOfLastShowedComment = Array.from(document.getElementsByClassName("_show-comment")).length - 1;
		if (indexOfLastShowedComment + 4 < commentsByUsers.length) {
			for (let i = indexOfLastShowedComment; i < indexOfLastShowedComment + 4; i++) {
				commentsByUsers[i].classList.add("_show-comment");
			}
		}
		else {
			for (let i = indexOfLastShowedComment; i < commentsByUsers.length; i++) {
				commentsByUsers[i].classList.add("_show-comment");
			}
		}
		hiddenButtonOfShowComments(commentsByUsers);
	});
}
pressShowMore(showMoreButton[0]);

const hiddenButtonOfShowComments = (items) => {
	let existHiddenComments = false;
	for (let i = 0; i < items.length; i++) {
		if(!items[i].classList.contains("_show-comment")) {
			existHiddenComments = true;
			return;
		}
	}
	if (!existHiddenComments) {
		showMoreButton[0].classList.add("_hidden");
	}
};