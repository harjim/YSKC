using System;
using System.ComponentModel.DataAnnotations;
namespace RSDB.Models
{
    public class docFileType : tablebase
    {
        [Key]
        public int id { get; set; }
        public int docFileId { get; set; }
        ///<summary>
        /// 文档类型：0：立项材料，1：月度轨迹材料，2：年度轨迹材料，3：验收材料
        ///</summary>
        public int docType { get; set; }
    }
}