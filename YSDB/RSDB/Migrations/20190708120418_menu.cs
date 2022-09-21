using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class menu : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "app_menu",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    parentId = table.Column<int>(nullable: false),
                    name = table.Column<string>(maxLength: 30, nullable: false),
                    type = table.Column<byte>(nullable: false),
                    level = table.Column<byte>(nullable: false),
                    url = table.Column<string>(maxLength: 200, nullable: true),
                    icon = table.Column<string>(maxLength: 30, nullable: false),
                    perms = table.Column<string>(nullable: true),
                    status = table.Column<byte>(nullable: false),
                    seq = table.Column<byte>(nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    fullPath = table.Column<string>(maxLength: 300, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_menu", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "app_menu");
        }
    }
}
