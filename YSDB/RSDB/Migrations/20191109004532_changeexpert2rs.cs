using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class changeexpert2rs : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "expert",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    expertNumber = table.Column<string>(maxLength: 30, nullable: false),
                    realName = table.Column<string>(maxLength: 15, nullable: false),
                    photoUrl = table.Column<string>(maxLength: 100, nullable: false),
                    birthday = table.Column<DateTime>(nullable: false),
                    level = table.Column<int>(nullable: false),
                    validDate = table.Column<DateTime>(nullable: true),
                    issueDate = table.Column<DateTime>(nullable: true),
                    idNumber = table.Column<string>(maxLength: 30, nullable: false),
                    gender = table.Column<byte>(nullable: false),
                    industryCode = table.Column<string>(maxLength: 20, nullable: false),
                    phone = table.Column<string>(maxLength: 30, nullable: false),
                    email = table.Column<string>(maxLength: 30, nullable: true),
                    policitalStatus = table.Column<byte>(nullable: false),
                    eduLevel = table.Column<int>(nullable: false),
                    address = table.Column<string>(maxLength: 100, nullable: false),
                    postcode = table.Column<string>(nullable: true),
                    workHistory = table.Column<string>(maxLength: 1000, nullable: false),
                    partHistory = table.Column<string>(maxLength: 1000, nullable: false),
                    honour = table.Column<string>(maxLength: 1000, nullable: true),
                    status = table.Column<byte>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    uuid = table.Column<string>(maxLength: 50, nullable: false),
                    qrcode = table.Column<string>(maxLength: 100, nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_expert", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "expert");
        }
    }
}
