using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPatentDataTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "patentData",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    patentName = table.Column<string>(maxLength: 200, nullable: true),
                    inventor = table.Column<string>(maxLength: 200, nullable: true),
                    applyName = table.Column<string>(maxLength: 300, nullable: true),
                    applyDateTime = table.Column<DateTime>(nullable: true),
                    mainType = table.Column<int>(nullable: true),
                    mainTypeNo = table.Column<string>(maxLength: 100, nullable: true),
                    caseStatus = table.Column<string>(maxLength: 50, nullable: true),
                    address = table.Column<string>(maxLength: 200, nullable: true),
                    publicNo = table.Column<string>(maxLength: 20, nullable: true),
                    publicDate = table.Column<DateTime>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patentData", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patentData");
        }
    }
}
