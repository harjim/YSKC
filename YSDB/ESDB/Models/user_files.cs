using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    ///<summary>
    /// 用户文件
    ///</summary>
    public class user_files : tablebase
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }
        ///<summary>
        ///文件名
        ///</summary>
        [Required, MaxLength(100)]
        public string filename { get; set; }
        ///<summary>
        ///文件路径
        ///</summary>
        [Required, MaxLength(200)]
        public string filePath { get; set; }
        ///<summary>
        /// 文件类型 0：半身证件照，1：身份证附件，2学历证明，3职称证书，4：其他证明资料
        /// </summary>
        public int type { get; set; }

    }
}
