// Burger functional
const burger = document.querySelector(".burger");
const burgerActive = () => {
	if (burger) {
		const menu = document.querySelector(".navbar");
		burger.addEventListener("click", () => {
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
		for (let i = 0; i <= index; i++) {
			stars[i].classList.remove("_checked-star");
			stars[i].classList.add("_checked-star");
		}
		for (let i = index + 1; i <= 4; i++) {
			stars[i].classList.remove("_checked-star");
		}
	});
};
stars.forEach(paintStars);

// Show more comments
const showMoreButton = document.querySelector(".comments__show-more");
const commentsByUsers = Array.from(document.querySelectorAll(".comment-by-user"));

const showComments = (items) => {
	if (items == null || showMoreButton == null) {
		return;
	}
	if (items.length <= 3) {
		showMoreButton.classList.add("_hidden");
	}
	if (items.length !== 0) {
		for (let i = 0; i < 3; i++) {
			items[i].classList.add("_show-comment");
		}
	}
};
showComments(commentsByUsers);

// Functional of button `Show more` in the comments
const pressShowMore = (button) => {
	if (button != null || commentsByUsers == null) {
		button.addEventListener("click", () => {
			const indexOfLastShowedComment = Array.from(document.getElementsByClassName("_show-comment")).length - 1;
			if (indexOfLastShowedComment + 4 < commentsByUsers.length) {
				for (let i = indexOfLastShowedComment; i < indexOfLastShowedComment + 4; i++) {
					commentsByUsers[i].classList.add("_show-comment");
				}
			} else {
				for (let i = indexOfLastShowedComment; i < commentsByUsers.length; i++) {
					commentsByUsers[i].classList.add("_show-comment");
				}
			}
			hiddenButtonOfShowComments(commentsByUsers);
		});
	}
}
pressShowMore(showMoreButton);

const hiddenButtonOfShowComments = (items) => {
	let existHiddenComments = false;
	for (let i = 0; i < items.length; i++) {
		if(!items[i].classList.contains("_show-comment")) {
			existHiddenComments = true;
			return;
		}
	}
	if (!existHiddenComments) {
		showMoreButton.classList.add("_hidden");
	}
};

//Code for profile page
const buttonsOfProductRow = document.querySelectorAll(".lists__navbar-item");
const listsOfProduct = document.querySelectorAll(".lists__list");

const pressProfileButton = (button, index) => {
	button.addEventListener("click", () => {
		if (button.classList.contains("_active")) return;
		for (let i = 0; i < buttonsOfProductRow.length; i++) {
			buttonsOfProductRow[i].classList.remove("_active");
		}
		for (let i = 0; i < listsOfProduct.length; i++) {
			listsOfProduct[i].classList.remove("_active");
		}
		listsOfProduct[index].classList.add("_active");
		button.classList.add("_active");
	});
};
buttonsOfProductRow.forEach(pressProfileButton);

// Set active class for users in the page of admin all_user
const listOfUsersInTheAdminPage = Array.from(document.querySelectorAll(".admin-user__table-item"));
const buttonShowMoreUser = document.querySelector(".admin-user__show-more");

const setActiveClassForFirstUsers = (lengthSet) => {
	if (listOfUsersInTheAdminPage.length === 0) return;
	let length = lengthSet;
	if (length > listOfUsersInTheAdminPage.length) length = listOfUsersInTheAdminPage.length;
	for (let i = 0; i < length; i++) {
		listOfUsersInTheAdminPage[i].classList.add("_active");
	}
};
setActiveClassForFirstUsers(15);

const pressShowMoreUsers = () => {
	if (buttonShowMoreUser != null) {
		buttonShowMoreUser.addEventListener("click", () => {
				const indexOfLastShowedComment = Array.from(document.getElementsByClassName("_active")).length - 1;
				let lengthActiveList = indexOfLastShowedComment + 10;
				if (lengthActiveList + 10 > listOfUsersInTheAdminPage.length) {
					lengthActiveList = listOfUsersInTheAdminPage.length;
				}
				for (let i = indexOfLastShowedComment; i < lengthActiveList; i++) {
					listOfUsersInTheAdminPage[i].classList.add("_active");
				}
			}
		);
	}
};
pressShowMoreUsers();

// Show main form of product

const buttonShowMainForm = document.querySelector(".search-product-use-main-form");
const mainForm = document.querySelector(".products-main-form");

const showMainForm = () => {
	if (buttonShowMainForm != null) {
		buttonShowMainForm.addEventListener("click", () => {
			buttonShowMainForm.classList.add("_hidden");
			mainForm.classList.add("_show");
		});
	}
};
showMainForm();

// Code for settings page
const pointsOfSettingsForm = Array.from(document.querySelectorAll(".form-settings__title"));
const formsOfSettings = Array.from(document.querySelectorAll(".form-settings"));

const showFormsOfSettings = (button, index) => {
	button.addEventListener("click", () => {
		for (let i = 0; i < pointsOfSettingsForm.length; i++) {
			pointsOfSettingsForm[i].classList.remove("_selected");
			formsOfSettings[i].classList.remove("_show");
		}
		pointsOfSettingsForm[index].classList.add("_selected");
		formsOfSettings[index].classList.add("_show");
	});
};
pointsOfSettingsForm.forEach(showFormsOfSettings);