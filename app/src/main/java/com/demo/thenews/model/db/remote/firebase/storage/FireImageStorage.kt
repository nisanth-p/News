package com.rion.homeautomation.model.db.remote.firebase.storage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.*
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import java.io.*

class FireImageStorage {

    fun getStorageRef(): StorageReference {
        val storage = Firebase.storage

        // ## Create a Reference

        // [START create_storage_reference]
        // Create a storage reference from our app
        return storage.reference
    }

/*    private fun includesForCreateReference() {
        val storage = Firebase.storage

        // ## Create a Reference

        // [START create_storage_reference]
        // Create a storage reference from our app
        var storageRef = storage.reference
        // [END create_storage_reference]

        // [START create_child_reference]
        // Create a child reference
        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("images")

        // Child references can also take paths
        // spaceRef now points to "images/space.jpg
        // imagesRef still points to "images"
        var spaceRef = storageRef.child("images/space.jpg")
        // [END create_child_reference]

        // ## Navigate with References

        // [START navigate_references]
        // parent allows us to move our reference to a parent node
        // imagesRef now points to 'images'
        imagesRef = spaceRef.parent

        // root allows us to move all the way back to the top of our bucket
        // rootRef now points to the root
        val rootRef = spaceRef.root
        // [END navigate_references]

        // [START chain_navigation]
        // References can be chained together multiple times
        // earthRef points to 'images/earth.jpg'
        val earthRef = spaceRef.parent?.child("earth.jpg")

        // nullRef is null, since the parent of root is null
        val nullRef = spaceRef.root.parent
        // [END chain_navigation]

        // ## Reference Properties

        // [START reference_properties]
        // Reference's path is: "images/space.jpg"
        // This is analogous to a file path on disk
        spaceRef.path

        // Reference's name is the last segment of the full path: "space.jpg"
        // This is analogous to the file name
        spaceRef.name

        // Reference's bucket is the name of the storage bucket that the files are stored in
        spaceRef.bucket
        // [END reference_properties]

        // ## Full Example

        // [START reference_full_example]
        // Points to the root reference
        storageRef = storage.reference

        // Points to "images"
        imagesRef = storageRef.child("images")

        // Points to "images/space.jpg"
        // Note that you can use variables to create child values
        val fileName = "space.jpg"
        spaceRef = imagesRef.child(fileName)

        // File path is "images/space.jpg"
        val path = spaceRef.path

        // File name is "space.jpg"
        val name = spaceRef.name

        // Points to "images"
        imagesRef = spaceRef.parent
        // [END reference_full_example]
    }

    fun includesForUploadFiles(func:()->Unit) {
        val storage = Firebase.storage

        // [START upload_create_reference]
        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child("mountains.jpg")

        // Create a reference to 'images/mountains.jpg'
        val mountainImagesRef = storageRef.child("images/mountains.jpg")

        // While the file names are the same, the references point to different files
        mountainsRef.name == mountainImagesRef.name // true
        mountainsRef.path == mountainImagesRef.path // false
        // [END upload_create_reference]

        val imageView = findViewById<ImageView>(R.id.imageView)
        // [START upload_memory]
        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
        // [END upload_memory]

        // [START upload_stream]
        val stream = FileInputStream(File("path/to/images/rivers.jpg"))

        uploadTask = mountainsRef.putStream(stream)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
        // [END upload_stream]

        // [START upload_file]
        var file = Uri.fromFile(File("path/to/images/rivers.jpg"))
        val riversRef = storageRef.child("images/${file.lastPathSegment}")
        uploadTask = riversRef.putFile(file)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
        // [END upload_file]

        // [START upload_with_metadata]
        // Create file metadata including the content type
        var metadata = storageMetadata {
            contentType = "image/jpg"
        }

        // Upload the file and metadata
        uploadTask = storageRef.child("images/mountains.jpg").putFile(file, metadata)
        // [END upload_with_metadata]

        // [START manage_uploads]
        uploadTask = storageRef.child("images/mountains.jpg").putFile(file)

        // Pause the upload
        uploadTask.pause()

        // Resume the upload
        uploadTask.resume()

        // Cancel the upload
        uploadTask.cancel()
        // [END manage_uploads]

        // [START monitor_upload_progress]
        // Observe state change events such as progress, pause, and resume
        // You'll need to import com.google.firebase.storage.ktx.component1 and
        // com.google.firebase.storage.ktx.component2
        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
            val progress = (100.0 * bytesTransferred) / totalByteCount
            Log.d(TAG, "Upload is $progress% done")
        }.addOnPausedListener {
            Log.d(TAG, "Upload is paused")
        }
        // [END monitor_upload_progress]

        // [START upload_complete_example]
        // File or Blob
        file = Uri.fromFile(File("path/to/mountains.jpg"))

        // Create the file metadata
        metadata = storageMetadata {
            contentType = "image/jpeg"
        }

        // Upload file and metadata to the path 'images/mountains.jpg'
        uploadTask = storageRef.child("images/${file.lastPathSegment}").putFile(file, metadata)

        // Listen for state changes, errors, and completion of the upload.
        // You'll need to import com.google.firebase.storage.ktx.component1 and
        // com.google.firebase.storage.ktx.component2
        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
            val progress = (100.0 * bytesTransferred) / totalByteCount
            Log.d(TAG, "Upload is $progress% done")
        }.addOnPausedListener {
            Log.d(TAG, "Upload is paused")
        }.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // Handle successful uploads on complete
            // ...
        }
        // [END upload_complete_example]

        // [START upload_get_download_url]
        val ref = storageRef.child("images/mountains.jpg")
        uploadTask = ref.putFile(file)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
            } else {
                // Handle failures
                // ...
            }
        }
        // [END upload_get_download_url]
    }

    fun includesForDownloadFiles() {
        val storage = Firebase.storage

        // [START download_create_reference]
        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("images/stars.jpg")

        // Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg")

        // Create a reference from an HTTPS URL
        // Note that in the URL, characters are URL escaped!
        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg")
        // [END download_create_reference]

        // [START download_to_memory]
        var islandRef = storageRef.child("images/island.jpg")

        val ONE_MEGABYTE: Long = 1024 * 1024
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            // Data for "images/island.jpg" is returned, use this as needed
        }.addOnFailureListener {
            // Handle any errors
        }
        // [END download_to_memory]

        // [START download_to_local_file]
        islandRef = storageRef.child("images/island.jpg")

        val localFile = File.createTempFile("images", "jpg")

        islandRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }
        // [END download_to_local_file]

        // [START download_via_url]
        storageRef.child("users/me/profile.png").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
        }.addOnFailureListener {
            // Handle any errors
        }
        // [END download_via_url]

        // [START download_full_example]
        storageRef.child("users/me/profile.png").getBytes(Long.MAX_VALUE).addOnSuccessListener {
            // Use the bytes to display the image
        }.addOnFailureListener {
            // Handle any errors
        }
        // [END download_full_example]
    }

    fun loadWithGlide() {
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference

        // ImageView in your Activity
        val imageView = findViewById<ImageView>(R.id.imageView)

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this *//* context *//*)
            .load(storageReference)
            .into(imageView)
        // [END storage_load_with_glide]
    }*/
}