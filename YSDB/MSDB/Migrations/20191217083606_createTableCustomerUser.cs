﻿using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createTableCustomerUser : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "customer_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    mType = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_customer_user", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "customer_user");
        }
    }
}
