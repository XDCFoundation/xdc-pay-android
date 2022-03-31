//SPDX-License-Identifier: Unlicense
pragma solidity ^0.8.0;


// PROXY MISSING
// MAKE IT UPGRADABLE -- {UPGRADABLE BEACON}
// ROYALTY
// EIP712MetaTransaction 



//import "hardhat/console.sol";
import "openzeppelin-contracts-master/contracts/token/ERC721/extensions/ERC721URIStorage.sol";

contract Ownable {
    address private _owner;

    event OwnershipTransferred(address indexed previousOwner, address indexed newOwner);

    /**
     * @dev Initializes the contract setting the deployer as the initial owner.
     */
    constructor ()  {
        _owner = msg.sender;
        emit OwnershipTransferred(address(0), _owner);
    }

    /**
     * @dev Returns the address of the current owner.
     */
    function owner() public view returns (address) {
        return _owner;
    }

    /**
     * @dev Throws if called by any account other than the owner.
     */
    modifier onlyOwner() {
        require(isOwner(), "Ownable: caller is not the owner");
        _;
    }

    /**
     * @dev Returns true if the caller is the current owner.
     */
    function isOwner() public view returns (bool) {
        return msg.sender == _owner;
    }

    /**
     * @dev Leaves the contract without owner. It will not be possible to call
     * `onlyOwner` functions anymore. Can only be called by the current owner.
     *
     * > Note: Renouncing ownership will leave the contract without an owner,
     * thereby removing any functionality that is only available to the owner.
     */
    function renounceOwnership() public onlyOwner {
        emit OwnershipTransferred(_owner, address(0));
        _owner = address(0);
    }

    /**
     * @dev Transfers ownership of the contract to a new account (`newOwner`).
     * Can only be called by the current owner.
     */
    function transferOwnership(address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }

    /**
     * @dev Transfers ownership of the contract to a new account (`newOwner`).
     */
    function _transferOwnership(address newOwner) internal {
        require(newOwner != address(0), "Ownable: new owner is the zero address");
        emit OwnershipTransferred(_owner, newOwner);
        _owner = newOwner;
    }
}

contract Fleek is ERC721URIStorage, Ownable {
  address payable admin;
  uint256 public comissionPercentage;

  constructor(address payable adminAccount,uint256 percentage)
    ERC721("NFTMarketPlace", "NFT") 
    {
      admin = adminAccount;
      comissionPercentage = percentage;
    }

  /*
  function to mint and transfer the nft
  */
  function mintAndTransfer(address signer, string memory message, bytes memory signature, uint256 tokenId, string memory uri, uint256 nftPrice) external payable {
    bytes32 _msgHash = getMessageHash(message);
    address payable _signer = payable(recover(_msgHash,signature));
    address redeemer = msg.sender;
   
    require(signer == _signer,"Invalid or unauthorized signature");
    require(redeemer.balance > nftPrice, "Insufficient funds to redeem");
    
  
    // first assign the token to the signer, to establish provenance on-chain
    _mint(_signer, tokenId);
    _setTokenURI(tokenId, uri);
    _transfer(_signer, redeemer, tokenId);
    _transferFunds(_signer,nftPrice);
  } 

  /*
  function to buy minted nft
  */
  function mintedNftSale(address signer, string memory message, bytes memory signature, uint256 tokenId, uint256 nftPrice) external payable {
    bytes32 _msgHash = getMessageHash(message);
    address payable _signer = payable(recover(_msgHash,signature));
    address receiver = msg.sender;
  
    require(signer == _signer,"Invalid or unauthorized signature");
    require((msg.sender).balance > nftPrice, "Insufficient funds to redeem");

    _safeTransfer(_signer,receiver,tokenId,"");
    _transferFunds(_signer,nftPrice);
  }

  /*
  Internal function which helps in transferring funds 
  */
  function _transferFunds(address payable seller,uint256 amount) internal  {
      uint _adminComission = amount * comissionPercentage / 100;
      uint _sellerComission = amount - _adminComission;
      if (!admin.send(_adminComission)) { 
            revert();
        }
      if (!seller.send(_sellerComission)) { 
            revert();
        }
  }

  /*
  function to change commision percentage and owner can only call this function
  */
  function changeComissionPercentage(uint256 value) external onlyOwner {
    comissionPercentage = value;
  }

  /*
  function to change commision receiver and owner can only call this function
  */
  function changePortalFeeReceiver(address payable addr) external onlyOwner{
      admin = addr;
  }


  function recover(bytes32 hash, bytes memory sig) internal pure returns (address) {
    bytes32 r;
    bytes32 s;
    uint8 v;

    //Check the signature length
    if (sig.length != 65) {
      return (address(0));
    }

    // Divide the signature in r, s and v variables
    assembly {
      r := mload(add(sig, 32))
      s := mload(add(sig, 64))
      v := byte(0, mload(add(sig, 96)))
    }

    // Version of signature should be 27 or 28, but 0 and 1 are also possible versions
    if (v < 27) {
      v += 27;
    }

    // If the version is correct return the signer address
    if (v != 27 && v != 28) {
      return (address(0));
    } else {
      return ecrecover(hash, v, r, s);
    }
  }

    function getMessageHash(
        string memory _message
    ) internal pure returns (bytes32) {
        return keccak256(abi.encodePacked(_message));
    }

}
