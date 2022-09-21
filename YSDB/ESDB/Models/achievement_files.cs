using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace ESDB.Models
{
    public class achievement_files : tablebase
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }
        ///<summary>
        /// 成果id
        ///</summary>
        public int achievementId { get; set; }
        ///<summary>
        /// 成果资料(多文件)
        ///</summary>
        [MaxLength(1000), Required]
        public string achievementFile { get; set; }

        ///<summary>
        /// 是否为代理人
        ///</summary>
        public bool agent { get; set; }

        ///<summary>
        /// 成果所属人姓名
        ///</summary>
        [MaxLength(50)]
        public string ownerName { get; set; }
        ///<summary>
        /// 代理协议文件
        ///</summary>
        [MaxLength(1000)]
        public string agentFile { get; set; }

        ///<summary>
        /// 是否已技术评定
        ///</summary>
        public bool assess { get; set; }

        ///<summary>
        /// 评定技术类型:0：节能环保，1：生物医药
        ///</summary>
        public int? assessType { get; set; }
        ///<summary>
        /// 证明文件（评定文件）
        ///</summary>
        [MaxLength(1000)]
        public string assessFile { get; set; }
        ///<summary>
        /// 小试
        ///</summary>
        public bool smallTest { get; set; }
        ///<summary>
        /// 小试证明文件
        ///</summary>
        [MaxLength(1000)]
        public string smallTestFile { get; set; }
        ///<summary>
        /// 中试
        ///</summary>
        public bool pilotTest { get; set; }
        ///<summary>
        /// 中试证明文件
        ///</summary>
        [MaxLength(1000)]
        public string pilotTestFile { get; set; }
    }
}