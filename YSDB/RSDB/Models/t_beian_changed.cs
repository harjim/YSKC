using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    
    /// <summary>
    /// 备案变更表
    /// </summary>
    public class t_beian_changed : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        
        ///<summary>
        /// 备案
        ///</summary>
        public int beianId { get; set; }
       ///<summary>
        /// 变更函签发时间(默认按签发时间正序)
        ///</summary>
        public DateTime? changeLetterDate { get; set; }
        ///<summary>
        /// 变更内容
        ///</summary>
        [MaxLength(2000)]
        public string changeContent { get; set; }

        ///<summary>
        /// 变更涵
        ///</summary>
        [MaxLength(200)]
        public string changeFilePath { get; set; }

    }
}