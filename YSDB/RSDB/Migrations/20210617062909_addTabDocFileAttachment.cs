using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addTabDocFileAttachment : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_docFile_attachment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    docFileId = table.Column<int>(nullable: false),
                    fileName = table.Column<string>(maxLength: 200, nullable: false),
                    filePath = table.Column<string>(maxLength: 200, nullable: true),
                    uploadTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_docFile_attachment", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_docFile_attachment");
        }
    }
}
